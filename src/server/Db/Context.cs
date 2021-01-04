using server.Models;
using Microsoft.EntityFrameworkCore;
using System;

namespace server.Db
{
    public class Context : DbContext
    {
        public const string CONNECTION_SECTION_NAME = "DbContext";

        public Context(DbContextOptions<Context> options)
            : base(options)
        {
        }

        public DbSet<Log> Log { get; set; }

        public DbSet<User> User { get; set; }

        public DbSet<RefreshToken> RefreshToken { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            new RawEntityTypeConfiguration().Configure(modelBuilder.Entity<Log>());
        }
    }
}
