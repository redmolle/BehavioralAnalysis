using System;

namespace server.Models.Base
{
    public class IdCreatedModel : IdModel, ICreated
    {
        public DateTime Created { get; set; } = DateTime.Now;
    }
}
