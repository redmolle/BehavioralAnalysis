using System;

namespace bas.Db.Repository.RefreshToken
{
    public interface IRefreshTokenRepository
    {
        void Remove(DateTime date);

        void Remove(string userName);

        Models.RefreshToken GetTokenOrNull(string token);
    }
}
