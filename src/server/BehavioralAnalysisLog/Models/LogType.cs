using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace BehavioralAnalysisLog.Models
{
    [JsonConverter(typeof(StringEnumConverter))]
    public enum LogType
    {
        app,

        call,

        contact,

        file,
        
        location,

        notification,

        granted_permission,

        sms,

        wifi,

        error
    }
}
