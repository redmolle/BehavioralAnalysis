using bas.Db.Factory;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;

namespace bas.Db.Repository.RefreshToken
{
    public class RefreshTokenRepository : Repository, IRefreshTokenRepository
    {
        public RefreshTokenRepository(IContextFactory contextFactory)
            : base(contextFactory)
        {
        }

        public Models.RefreshToken GetTokenOrNull(string token)
        {
            if (string.IsNullOrEmpty(token))
            {
                return null;
            }

            using (var context = _contextFactory.CreateDbContext())
            {
                return context.RefreshToken.Include(x => x.User).FirstOrDefault(x => x.TokenString == token);
            }
        }

        public void Remove(DateTime date)
        {
            var now = DateTime.Now;

            using (var context = _contextFactory.CreateDbContext())
            {
                var toRemove = context.RefreshToken.Where(x => date > x.Stopped && x.ExpireAt < date);
                foreach (var remove in toRemove)
                {
                    remove.Stopped = now;
                }
                context.SaveChanges();
            }
        }

        public void Remove(string userName)
        {
            var now = DateTime.Now;
            using (var context = _contextFactory.CreateDbContext())
            {
                var toRemove = context.RefreshToken.Include(x => x.User).Where(x => x.User.Name.ToLower() == userName.ToLower() && x.Stopped > now);
                foreach (var remove in toRemove)
                {
                    remove.Stopped = now;
                }
                context.SaveChanges();
            }
        }
    }
}
