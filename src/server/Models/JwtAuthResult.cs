using Newtonsoft.Json;

namespace server.Models
{
    public class JwtAuthResult
    {
        [JsonProperty("accessToken")]
        public string AccessToken { get; set; }

        [JsonProperty("refreshToken")]
        public RefreshToken RefreshToken { get; set; }
    }
}
