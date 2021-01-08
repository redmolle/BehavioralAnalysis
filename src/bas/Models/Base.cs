using Newtonsoft.Json;
using System;

namespace bas.Models
{
    public abstract class Base
    {
        [JsonProperty("id")]
        public Guid Id { get; set; }

        [JsonProperty("created")]
        public DateTime Created { get; set; } = DateTime.Now;
    }
}
