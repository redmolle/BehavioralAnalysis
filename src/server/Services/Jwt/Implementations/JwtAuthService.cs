using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.IdentityModel.Tokens;
using server.Db;
using server.Models;
using server.Services.Jwt.Interfaces;
using System;
using System.Collections.Immutable;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;

namespace server.Services.Jwt.Implementations
{
    public class JwtAuthService : IJwtAuthService
    {
        public JwtAuthService(JwtTokenConfig jwtTokenConfig, IServiceScopeFactory serviceScopeFactory)
        {
            _jwtTokenConfig = jwtTokenConfig;
            _secret = Encoding.ASCII.GetBytes(jwtTokenConfig.Secret);
            _serviceScopeFactory = serviceScopeFactory;
        }

        public IImmutableDictionary<string, RefreshToken> UsersRefreshTokensReadOnlyDictionary => throw new NotImplementedException();

        private readonly JwtTokenConfig _jwtTokenConfig;
        private readonly byte[] _secret;
        private readonly IServiceScopeFactory _serviceScopeFactory;

        public (ClaimsPrincipal, JwtSecurityToken) DecodeJwtToken(string token, bool validateLifetime = true)
        {
            if (string.IsNullOrWhiteSpace(token))
            {
                throw new SecurityTokenException("Invalid token");
            }
            var principal = new JwtSecurityTokenHandler()
                .ValidateToken(token,
                    new TokenValidationParameters
                    {
                        ValidateIssuer = true,
                        ValidIssuer = _jwtTokenConfig.Issuer,
                        ValidateIssuerSigningKey = true,
                        IssuerSigningKey = new SymmetricSecurityKey(_secret),
                        ValidAudience = _jwtTokenConfig.Audience,
                        ValidateAudience = true,
                        ValidateLifetime = validateLifetime,
                        ClockSkew = TimeSpan.FromMinutes(1)
                    },
                    out var validatedToken);
            return (principal, validatedToken as JwtSecurityToken);
        }

        public JwtAuthResult GenerateTokens(string username, Claim[] claims, DateTime now)
        {
            var shouldAddAudienceClaim = string.IsNullOrWhiteSpace(claims?.FirstOrDefault(x => x.Type == JwtRegisteredClaimNames.Aud)?.Value);
            var jwtToken = new JwtSecurityToken(
                _jwtTokenConfig.Issuer,
                shouldAddAudienceClaim ? _jwtTokenConfig.Audience : string.Empty,
                claims,
                expires: now.AddMinutes(_jwtTokenConfig.AccessTokenExpiration),
                signingCredentials: new SigningCredentials(new SymmetricSecurityKey(_secret), SecurityAlgorithms.HmacSha256Signature));
            var accessToken = new JwtSecurityTokenHandler().WriteToken(jwtToken);

            RefreshToken refreshToken = null;

            ExecWithContext(context =>
            {
                var user = context.User.FirstOrDefault(x => x.Name == username);
                refreshToken = new RefreshToken
                {
                    User = user,
                    TokenString = GenerateRefreshTokenString(),
                    ExpireAt = now.AddMinutes(_jwtTokenConfig.RefreshTokenExpiration)
                };
                context.RefreshToken.Add(refreshToken);
                context.SaveChanges();
            });


            return new JwtAuthResult
            {
                AccessToken = accessToken,
                RefreshToken = refreshToken
            };
        }

        public JwtAuthResult Refresh(string refreshToken, string accessToken, DateTime now)
        {
            var (principal, jwtToken) = DecodeJwtToken(accessToken, false);
            if (jwtToken == null || !jwtToken.Header.Alg.Equals(SecurityAlgorithms.HmacSha256Signature))
            {
                throw new SecurityTokenException("Invalid token");
            }

            var userName = principal.Identity.Name;

            RefreshToken existingRefreshToken = ExecWithContext<RefreshToken>(context =>
            {
                return context.RefreshToken.Include(x => x.User).FirstOrDefault(x => x.TokenString == refreshToken);
            });

            if (existingRefreshToken == null)
            {
                throw new SecurityTokenException("Invalid token");
            }
            if (existingRefreshToken.User.Name != userName || existingRefreshToken.ExpireAt < now)
            {
                throw new SecurityTokenException("Invalid token");
            }

            return GenerateTokens(userName, principal.Claims.ToArray(), now);
        }

        public void RemoveExpiredRefreshTokens(DateTime now)
        {
            ExecWithContext(context =>
            {
                var expiredTokens = context.RefreshToken.Where(x => x.ExpireAt < now).ToList();
                foreach (var expiredToken in expiredTokens)
                {
                    expiredToken.Stopped = DateTime.Now;
                }
                context.SaveChanges();
            });
        }

        public void RemoveRefreshTokenByUserName(string userName)
        {
            ExecWithContext(context =>
            {
                var refreshTokens = context.RefreshToken.Where(x => x.User.Name == userName).ToList();

                foreach (var refreshToken in refreshTokens)
                {
                    refreshToken.Stopped = DateTime.Now;
                }
                context.SaveChanges();
            });
        }

        private delegate void ContextMethod(Context context);

        private delegate T ContextMethod<T>(Context context);

        private void ExecWithContext(ContextMethod method)
        {
            using (var scope = _serviceScopeFactory.CreateScope())
            {
                var context = scope.ServiceProvider.GetRequiredService<Context>();
                method(context);
            }
        }

        private T ExecWithContext<T>(ContextMethod<T> method)
        {
            using (var scope = _serviceScopeFactory.CreateScope())
            {
                var context = scope.ServiceProvider.GetRequiredService<Context>();
                return method(context);
            }
        }

        private static string GenerateRefreshTokenString()
        {
            var randomNumber = new byte[32];
            using var randomNumberGenerator = RandomNumberGenerator.Create();
            randomNumberGenerator.GetBytes(randomNumber);
            return Convert.ToBase64String(randomNumber);
        }
    }
}
