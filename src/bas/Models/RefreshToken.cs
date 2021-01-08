using Newtonsoft.Json;
using System;

namespace bas.Models
{
    public class RefreshToken : Base
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
