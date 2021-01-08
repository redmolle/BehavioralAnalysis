using Newtonsoft.Json;

namespace bas.Models
{
    public class JwtTokenConfig
    {
        public const string SECTION_NAME = "JwtTokenConfig";

        [JsonProperty("secret")]
        public string Secret { get; set; }

        [JsonProperty("issuer")]
        public string Issuer { get; set; }

        [JsonProperty("audience")]
        public string Audience { get; set; }

        [JsonProperty("accessTokenExpiration")]
        public int AccessTokenExpiration { get; set; }

        [JsonProperty("refreshTokenExpiration")]
        public int RefreshTokenExpiration { get; set; }
    }
}
