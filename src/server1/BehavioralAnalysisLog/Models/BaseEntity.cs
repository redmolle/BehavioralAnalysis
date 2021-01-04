using Newtonsoft.Json;
using System;

namespace server.Models
{
    public class BaseEntity
    {
        [JsonProperty("id")]
        public Guid Id { get; set; }

        [JsonProperty("created")]
        public DateTime Created { get; set; }

        [JsonProperty("updated")]
        public DateTime Updated { get; set; }
    }
}
