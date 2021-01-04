using Newtonsoft.Json;
using server.Models.Base;
using System;
using System.Collections.Generic;

namespace server.Models
{
    public class User : IdCreatedModel
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
