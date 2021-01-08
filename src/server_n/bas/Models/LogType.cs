using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace bas.Models
{
    [JsonConverter(typeof(StringEnumConverter))]
    public enum LogType
    {
        none = -1,

        app = 0,

        call = 1,

        contact = 2,

        file = 3,
        
        location = 4,

        notification = 5,

        granted_permission = 6,

        sms = 7,

        wifi = 8,

        error = 9
    }
}
