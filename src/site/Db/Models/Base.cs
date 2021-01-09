using Newtonsoft.Json;
using System;

namespace site.Db.Models
{
    public abstract class Base
    {
        [JsonProperty("id")]
        public Guid Id { get; set; }

        [JsonProperty("created")]
        public DateTime Created { get; set; } = DateTime.Now;
    }
}