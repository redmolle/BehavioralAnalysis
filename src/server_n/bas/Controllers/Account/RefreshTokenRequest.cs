using Newtonsoft.Json;

namespace bas.Controllers.Account
{
    public class RefreshTokenRequest
    {
        [JsonProperty("refreshToken")]
        public string RefreshToken { get; set; }
    }
}
