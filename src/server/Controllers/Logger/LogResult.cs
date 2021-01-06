using Newtonsoft.Json;
using System;

namespace server.Controllers.Logger
{
    public class LogResult
    {
        [JsonProperty("id")]
        public string Id { get; set; }

        [JsonProperty("type")]
        public string Type { get; set; }

        [JsonProperty("value")]
        public string Value { get; set; }

        [JsonProperty("created")]
        public DateTime Created { get; set; }
    }
}
