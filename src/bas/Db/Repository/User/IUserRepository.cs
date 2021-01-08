namespace bas.Db.Repository.User
{
    public interface IUserRepository
    {
        Models.User GetUserOrNull(string name);
    }
}
