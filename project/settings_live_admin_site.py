from project.settings_live_base import *


LAYERS['layers'] = ('basic', 'web')
STATIC_ROOT = abspath("..",  "games-static", "web")
STATIC_URL = '/static/web/'
