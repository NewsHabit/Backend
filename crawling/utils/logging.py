import logging
import logging.config
import json

loggerConfig = json.load(open('./config/logger.json'))
logging.config.dictConfig(loggerConfig)
logger = logging.getLogger(__name__)
