using Newtonsoft.Json;
using System;

namespace BehavioralAnalysisLog.Models
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
