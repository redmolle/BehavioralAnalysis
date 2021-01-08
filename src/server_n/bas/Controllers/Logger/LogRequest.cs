using Newtonsoft.Json;
using bas.Models;

namespace bas.Controllers.Logger
{
    public class LogRequest
    {
        [JsonProperty("value")]
        public object Value { get; set; }

        [JsonProperty("type")]
        public LogType Type { get; set; }

        [JsonProperty("deviceId")]
        public string DeviceId { get; set; }

        [JsonProperty("date")]
        public string Date { get; set; }
    }
}
