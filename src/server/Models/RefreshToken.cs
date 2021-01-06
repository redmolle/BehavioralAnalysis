using Newtonsoft.Json;
using server.Models.Base;
using System;

namespace server.Models
{
    public class RefreshToken : IdCreatedModel
    {
        [JsonProperty("tokenString")]
        public string TokenString { get; set; }

        [JsonProperty("expireAt")]
        public DateTime ExpireAt { get; set; }

        [JsonProperty("stopped")]
        public DateTime Stopped { get; set; } = DateTime.MaxValue;

        [JsonProperty("user")]
        public virtual User User { get; set; }
    }
}
