using Microsoft.IdentityModel.Tokens;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using bas.Models;
using bas.Db.Repository.RefreshToken;
using Microsoft.Extensions.DependencyInjection;

namespace bas.Services.Jwt
{
    public class JwtService : IJwtService
    {
        public JwtService(JwtTokenConfig jwtTokenConfig, IServiceScopeFactory serviceScopeFactory)
        {
            _jwtTokenConfig = jwtTokenConfig;
            _secret = Encoding.ASCII.GetBytes(jwtTokenConfig.Secret);
            _serviceScopeFactory = serviceScopeFactory;
        }

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
            throw new NotImplementedException();
        }

        public JwtAuthResult Refresh(string refreshToken, string accessToken, DateTime now)
        {
            var (principal, jwtToken) = DecodeJwtToken(accessToken, false);
            if (jwtToken == null || !jwtToken.Header.Alg.Equals(SecurityAlgorithms.HmacSha256Signature))
            {
                throw new SecurityTokenException("Invalid token");
            }

            var userName = principal.Identity.Name;

            using (var scope = _serviceScopeFactory.CreateScope())
            {
                var refreshTokenRepository = scope.ServiceProvider.GetRequiredService<IRefreshTokenRepository>();
                var token = refreshTokenRepository.GetTokenOrNull(refreshToken);

                if (token == null || token.User.Name != userName || token.ExpireAt < now)
                {
                    throw new SecurityTokenException("Invalid token");
                }

                return GenerateTokens(userName, principal.Claims.ToArray(), now);
            }
        }

        public void RemoveRefreshTokenByUserName(string userName)
        {
            using (var scope = _serviceScopeFactory.CreateScope())
            {
                var refreshTokenRepository = scope.ServiceProvider.GetRequiredService<IRefreshTokenRepository>();
                refreshTokenRepository.Remove(userName);
            }
        }
    }
}
