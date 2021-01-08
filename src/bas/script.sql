IF OBJECT_ID(N'[__EFMigrationsHistory]') IS NULL
BEGIN
    CREATE TABLE [__EFMigrationsHistory] (
        [MigrationId] nvarchar(150) NOT NULL,
        [ProductVersion] nvarchar(32) NOT NULL,
        CONSTRAINT [PK___EFMigrationsHistory] PRIMARY KEY ([MigrationId])
    );
END;

GO

CREATE TABLE [Log] (
    [Id] uniqueidentifier NOT NULL,
    [Created] datetime2 NOT NULL,
    [Value] nvarchar(max) NULL,
    [Type] int NOT NULL,
    [DeviceId] nvarchar(max) NULL,
    CONSTRAINT [PK_Log] PRIMARY KEY ([Id])
);

GO

CREATE TABLE [User] (
    [Id] uniqueidentifier NOT NULL,
    [Created] datetime2 NOT NULL,
    [Name] nvarchar(450) NULL,
    [Password] nvarchar(max) NULL,
    [IsAdmin] bit NOT NULL,
    CONSTRAINT [PK_User] PRIMARY KEY ([Id])
);

GO

CREATE TABLE [RefreshToken] (
    [Id] uniqueidentifier NOT NULL,
    [Created] datetime2 NOT NULL,
    [TokenString] nvarchar(450) NULL,
    [ExpireAt] datetime2 NOT NULL,
    [Stopped] datetime2 NOT NULL,
    [UserId] uniqueidentifier NOT NULL,
    CONSTRAINT [PK_RefreshToken] PRIMARY KEY ([Id]),
    CONSTRAINT [FK_RefreshToken_User_UserId] FOREIGN KEY ([UserId]) REFERENCES [User] ([Id]) ON DELETE CASCADE
);

GO

CREATE UNIQUE INDEX [IX_RefreshToken_TokenString] ON [RefreshToken] ([TokenString]) WHERE [TokenString] IS NOT NULL;

GO

CREATE INDEX [IX_RefreshToken_UserId] ON [RefreshToken] ([UserId]);

GO

CREATE UNIQUE INDEX [IX_User_Name] ON [User] ([Name]) WHERE [Name] IS NOT NULL;

GO

INSERT INTO [__EFMigrationsHistory] ([MigrationId], [ProductVersion])
VALUES (N'20210108033038_Init', N'3.1.10');

GO

