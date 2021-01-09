using Newtonsoft.Json;
using site.Db.Models;

namespace site.Models
{
    public class LogRequest
    {
        [JsonProperty("value")]
        public object Value { get; set; }

        [JsonProperty("type")]
        public string Type { get; set; }

        [JsonProperty("deviceId")]
        public string DeviceId { get; set; }

        [JsonProperty("date")]
        public string Date { get; set; }
    }
}
