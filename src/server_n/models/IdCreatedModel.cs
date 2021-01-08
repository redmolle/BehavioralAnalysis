using System;

namespace models
{
    public class IdCreatedModel : IdModel, ICreated
    {
        public DateTime Created { get; set; } = DateTime.Now;
    }
}
