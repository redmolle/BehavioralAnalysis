using bas.Db.Factory;

namespace bas.Db.Repository
{
    public abstract class Repository
    {
        public Repository(IContextFactory contextFactory)
        {
            _contextFactory = contextFactory;
        }

        protected IContextFactory _contextFactory;
    }
}
