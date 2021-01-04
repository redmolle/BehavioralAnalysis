using server.Models;
using Microsoft.EntityFrameworkCore;
using System;

namespace server.Db
{
    public class LogContext : DbContext
    {
        public LogContext(DbContextOptions<LogContext> options)
            : base(options)
        {
        }

        public DbSet<RawLog> Raw { get; set; }

        public DbSet<App> App { get; set; }

        public DbSet<Call> Call { get; set; }

        public DbSet<Contact> Contact { get; set; }

        public DbSet<Location> Location { get; set; }

        public DbSet<Notification> Notification { get; set; }

        public DbSet<Sms> Sms { get; set; }

        public DbSet<Wifi> Wifi { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            new RawEntityTypeConfiguration().Configure(modelBuilder.Entity<RawLog>());
            new AppEntityTypeConfiguration().Configure(modelBuilder.Entity<App>());
            new CallEntityTypeConfiguration().Configure(modelBuilder.Entity<Call>());
            new ContactEntityTypeConfiguration().Configure(modelBuilder.Entity<Contact>());
            new LocationEntityTypeConfiguration().Configure(modelBuilder.Entity<Location>());
            new NotificationEntityTypeConfiguration().Configure(modelBuilder.Entity<Notification>());
            new SmsEntityTypeConfiguration().Configure(modelBuilder.Entity<Sms>());
            new WifiEntityTypeConfiguration().Configure(modelBuilder.Entity<Wifi>());
        }

        public override int SaveChanges()
        {
            OnBeforeSaving();
            return base.SaveChanges();
        }

        private void OnBeforeSaving()
        {
            var entries = ChangeTracker.Entries();
            var utcNow = DateTime.UtcNow;

            foreach (var entry in entries)
            {
                if (entry.Entity is BaseEntity trackable)
                {
                    switch (entry.State)
                    {
                        case EntityState.Modified:
                            trackable.Updated = utcNow;
                            entry.Property("Created").IsModified = false;
                            break;

                        case EntityState.Added:
                            trackable.Created = utcNow;
                            trackable.Updated = utcNow;
                            break;
                    }
                }
            }
        }
    }
}
