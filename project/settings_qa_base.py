from project.settings import *


DATABASES = {
    'default': {
        'ENGINE': 'django.contrib.gis.db.backends.postgis',
        'NAME': 'games',
        'USER': 'games',
        'PASSWORD': 'games',
        'HOST': 'localhost',
        'PORT': '5432',
    }
}

MEDIA_ROOT = abspath("..",  "games-media")
STATIC_ROOT = abspath("..",  "games-static")
CKEDITOR_UPLOAD_PATH = os.path.join(MEDIA_ROOT, "uploads")

CACHES = {
    'default': {
        'BACKEND': 'django.core.cache.backends.memcached.MemcachedCache',
        'LOCATION': '127.0.0.1:11211',
        'KEY_PREFIX': 'games_qa',
    }
}

INSTALLED_APPS += ("atlas", "django.contrib.gis")

ALLOWED_HOSTS = [
    ".site.com"
]
