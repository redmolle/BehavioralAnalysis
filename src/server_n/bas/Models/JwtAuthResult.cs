using Newtonsoft.Json;

namespace bas.Models
{
    public class JwtAuthResult
    {
        [JsonProperty("accessToken")]
        public string AccessToken { get; set; }

        [JsonProperty("refreshToken")]
        public RefreshToken RefreshToken { get; set; }
    }
}
