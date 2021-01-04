using Microsoft.Extensions.Hosting;
using server.Services.Jwt.Interfaces;
using System;
using System.Threading;
using System.Threading.Tasks;

namespace server.Services.Jwt.Implementations
{
    public class JwtRefreshTokenCache : IHostedService, IDisposable
    {
        public JwtRefreshTokenCache(IJwtAuthService jwtAuthManager)
        {
            _jwtAuthManager = jwtAuthManager;
        }

        private Timer _timer;
        private readonly IJwtAuthService _jwtAuthManager;

        public void Dispose()
        {
            _timer?.Dispose();
        }

        public Task StartAsync(CancellationToken cancellationToken)
        {
            _timer = new Timer(DoWork, null, TimeSpan.Zero, TimeSpan.FromMinutes(1));
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
