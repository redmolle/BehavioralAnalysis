using System;

namespace bas.Models.Base
{
    public class IdCreatedModel : IdModel, ICreated
    {
        public DateTime Created { get; set; } = DateTime.Now;
    }
}
