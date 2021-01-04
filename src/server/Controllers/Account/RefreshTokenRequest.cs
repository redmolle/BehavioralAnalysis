using Newtonsoft.Json;

namespace server.Controllers.Account
{
    public class RefreshTokenRequest
    {
        [JsonProperty("refreshToken")]
        public string RefreshToken { get; set; }
    }
}
