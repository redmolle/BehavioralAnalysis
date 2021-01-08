using bas.Models;
using bas.Services.Jwt;
using bas.Services.User;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System;
using System.Security.Claims;
using System.Threading.Tasks;

namespace bas.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        //public AccountController(IUserService userService, IJwtService jwtService)
        //{
        //    _userService = userService;
        //    _jwtService = jwtService;
        //}

        //private readonly IUserService _userService;

        //private readonly IJwtService _jwtService;


        //[AllowAnonymous]
        //[HttpPost("login")]
        //public IActionResult Login([FromBody] LoginRequest request)
        //{
        //    try
        //    {
        //        if (!_userService.IsValidUserCredentials(request.UserName, request.Password))
        //        {
        //            return NoContent();
        //        }

        //        var role = _userService.GetUserRole(request.UserName);
        //        var claims = new[]
        //        {
        //        new Claim(ClaimTypes.Name,request.UserName),
        //        new Claim(ClaimTypes.Role, role)
        //    };

        //        var jwtResult = _jwtService.GenerateTokens(request.UserName, claims, DateTime.Now);
        //    return Ok(new LoginResult
        //    {
        //        UserName = request.UserName,
        //        Role = role,
        //        AccessToken = jwtResult.AccessToken,
        //        RefreshToken = jwtResult.RefreshToken.TokenString
        //    });
        //    }
        //    catch (Exception ex)
        //    {
        //        return BadRequest(ex.Message + "\n" + ex.StackTrace);
        //    }
        //}

        //[HttpGet("user")]
        //[Authorize]
        //public ActionResult GetCurrentUser()
        //{
        //    return Ok(new LoginResult
        //    {
        //        UserName = User.Identity.Name,
        //        Role = User.FindFirst(ClaimTypes.Role)?.Value ?? string.Empty,
        //        OriginalUserName = User.FindFirst("OriginalUserName")?.Value
        //    });
        //}

        //[HttpPost("logout")]
        //[Authorize]
        //public ActionResult Logout()
        //{
        //    var userName = User.Identity.Name;
        //    _jwtService.RemoveRefreshTokenByUserName(userName);
        //    return Ok();
        //}

        //[HttpPost("refresh-token")]
        //public async Task<ActionResult> RefreshToken([FromBody] RefreshTokenRequest request)
        //{
        //    try
        //    {
        //        var userName = User.Identity.Name;

        //        if (string.IsNullOrWhiteSpace(request.RefreshToken))
        //        {
        //            return Unauthorized();
        //        }

        //        var accessToken = await HttpContext.GetTokenAsync("Bearer", "access_token");
        //        if (string.IsNullOrWhiteSpace(accessToken) &&
        //            Request.Headers.TryGetValue("Authorization", out var tmp))
        //        {
        //            var token = tmp.ToString();
        //            if (token.StartsWith(JwtBearerDefaults.AuthenticationScheme))
        //            {
        //                var length = JwtBearerDefaults.AuthenticationScheme.Length + 1;
        //                accessToken = token.Substring(length, token.Length - length);
        //            }
        //        }

        //        var jwtResult = _jwtService.Refresh(request.RefreshToken, accessToken, DateTime.Now);
        //        return Ok(new LoginResult
        //        {
        //            UserName = userName,
        //            Role = User.FindFirst(ClaimTypes.Role)?.Value ?? string.Empty,
        //            AccessToken = jwtResult.AccessToken,
        //            RefreshToken = jwtResult.RefreshToken.TokenString
        //        });
        //    }
        //    catch (SecurityTokenException e)
        //    {
        //        return Unauthorized(e.Message);
        //    }
        //}
    }
}
