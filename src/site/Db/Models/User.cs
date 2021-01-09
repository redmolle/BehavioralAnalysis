using Newtonsoft.Json;
using System.Collections.Generic;

namespace site.Db.Models
{
    public class User : Base
    {
        [JsonProperty("name")]
        public string Name { get; set; }

        [JsonProperty("password")]
        public string Password { get; set; }

        [JsonProperty("isAdmin")]
        public bool IsAdmin { get; set; }

        [JsonProperty("refreshTokens")]
        public List<RefreshToken> RefreshTokens { get; set; }
    }
}