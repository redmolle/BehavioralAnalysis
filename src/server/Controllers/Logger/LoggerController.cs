using server.Db;
using server.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace server.Controllers.Logger
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoggerController : ControllerBase
    {
        public LoggerController(Context logContext)
        {
            this.logContext = logContext;
        }

        Context logContext = null;

        [HttpPost]
        public IActionResult Log([FromBody]object request)
        {
            //switch (JsonConvert.DeserializeObject<LogBase>(request.ToString()).Type)
            //{
            //    case LogType.app:
            //        foreach (var app in JsonConvert.DeserializeObject<Log<List<App>>>(request.ToString()).Value)
            //        {
            //            logContext.App.Add(app);
            //        }
            //        break;
            //    case LogType.call:
            //        foreach (var call in JsonConvert.DeserializeObject<Log<List<Call>>>(request.ToString()).Value)
            //        {
            //            logContext.Call.Add(call);
            //        }
            //        break;
            //    case LogType.contact:
            //        foreach (var contact in JsonConvert.DeserializeObject<Log<List<Contact>>>(request.ToString()).Value)
            //        {
            //            logContext.Contact.Add(contact);
            //        }
            //        break;
            //    case LogType.location:
            //        logContext.Location.Add(JsonConvert.DeserializeObject<Log<Location>>(request.ToString()).Value);
            //        break;
            //    case LogType.notification:
            //        logContext.Notification.Add(JsonConvert.DeserializeObject<Log<Notification>>(request.ToString()).Value);
            //        break;
            //    case LogType.sms:
            //        foreach (var sms in JsonConvert.DeserializeObject<Log<List<Sms>>>(request.ToString()).Value)
            //        {
            //            logContext.Sms.Add(sms);
            //        }
            //        break;
            //    case LogType.wifi:
            //        foreach (var wifi in JsonConvert.DeserializeObject<Log<List<Wifi>>>(request.ToString()).Value)
            //        {
            //            logContext.Wifi.Add(wifi);
            //        }
            //        break;
            //}

            var log = JsonConvert.DeserializeObject<Log>(request.ToString());

            logContext.Log.Add(log);

            logContext.SaveChanges();

            return CreatedAtAction("Get", log.Id);
        }

        [HttpGet]
        [Route("all")]
        public IActionResult Get()
        {
            return Ok(logContext.Log.ToList());
        }

        [HttpGet]
        [Route("{type}")]
        public IActionResult Get(string type)
        {
            LogType logType;
            if (Enum.TryParse(type, out logType) &&
                Enum.IsDefined(typeof(LogType), logType))
            {
                return Ok(logContext.Log.Where(x => x.Type == logType).ToList());
            }
            else
            {
                return NotFound($"Not found type [{type}]");
            }
        }
    }
}
