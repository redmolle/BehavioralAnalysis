using Newtonsoft.Json;
using server.Models.Base;
using System;

namespace server.Models
{
    public class Log : IdCreatedModel
    {
        [JsonProperty("value")]
        public object Value { get; set; }

        private LogType? type = null;

        [JsonProperty("type")]
        public LogType Type { get => type ?? LogType.none; set => type = value; }
    }
}
