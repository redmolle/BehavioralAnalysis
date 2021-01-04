using System;

namespace server.Models.Base
{
    public class IdCreatedModel : IdModel, ICreated
    {
        private DateTime? _created = null;
        public DateTime Created { get => _created ?? DateTime.Now; set => _created = value; }
    }
}
