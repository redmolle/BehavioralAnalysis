using Newtonsoft.Json;

namespace bas.Models
{
    public class RefreshTokenRequest
    {
        [JsonProperty("refreshToken")]
        public string RefreshToken { get; set; }
    }
}
