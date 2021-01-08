using Microsoft.Extensions.Hosting;
using System;
using System.Threading;
using System.Threading.Tasks;

namespace bas.Services.Jwt
{
    public class JwtRefreshTokenCacheService : IHostedService, IDisposable
    {
        public JwtRefreshTokenCacheService(IJwtService jwtAuthManager)
        {
            _jwtAuthManager = jwtAuthManager;
        }

        private Timer _timer;
        private readonly IJwtService _jwtAuthManager;

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
            _jwtAuthManager.RemoveExpiredRefreshTokens(DateTime.Now);
        }
    }
}
