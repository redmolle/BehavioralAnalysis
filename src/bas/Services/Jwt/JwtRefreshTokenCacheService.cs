using bas.Db.Repository.RefreshToken;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System;
using System.Threading;
using System.Threading.Tasks;

namespace bas.Services.Jwt
{
    public class JwtRefreshTokenCacheService : IHostedService, IDisposable
    {
        public JwtRefreshTokenCacheService(IServiceProvider services)
        {
            _services = services;
        }

        private Timer _timer;
        private readonly IServiceProvider _services;

        public void Dispose()
        {
            _timer?.Dispose();
        }

        public Task StartAsync(CancellationToken cancellationToken)
        {
            _timer = new Timer(DoWork, null, TimeSpan.Zero, TimeSpan.FromMinutes(15));
            return Task.CompletedTask;
        }

        public Task StopAsync(CancellationToken cancellationToken)
        {
            _timer?.Change(Timeout.Infinite, 0);
            return Task.CompletedTask;
        }

        private void DoWork(object state)
        {
            using (var scope = _services.CreateScope())
            {
                var refreshTokenRepository = scope.ServiceProvider.GetRequiredService<IRefreshTokenRepository>();

                refreshTokenRepository.Remove(DateTime.Now);
            }
        }
    }
}
