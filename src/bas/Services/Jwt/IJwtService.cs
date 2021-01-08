using bas.Models;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;

namespace bas.Services.Jwt
{
    public interface IJwtService
    {
        JwtAuthResult GenerateTokens(string username, Claim[] claims, DateTime now);
        JwtAuthResult Refresh(string refreshToken, string accessToken, DateTime now);
        void RemoveRefreshTokenByUserName(string userName);
        void RemoveExpiredRefreshTokens(DateTime date);
        (ClaimsPrincipal, JwtSecurityToken) DecodeJwtToken(string token, bool validateLifetime = true);
    }
}
