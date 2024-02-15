class News :
	__description = ""
	__imgLink = ""
	__naverLink = ""
	__title = ""
	__pubDate = ""

	def __init__(self) :
		pass

	@property
	def description(self) -> str :
		return self.__description

	@description.setter
	def description(self, description : str) -> None :
		self.__description = description

	@property
	def imgLink(self) -> str :
		return self.__imgLink

	@imgLink.setter
	def imgLink(self, imgLink : str) -> None :
		self.__imgLink = imgLink

	@property
	def naverLink(self) -> str :
		return self.__naverLink

	@naverLink.setter
	def naverLink(self, naverLink : str) -> None :
		self.__naverLink = naverLink

	@property
	def title(self) -> str :
		return self.__title

	@title.setter
	def title(self, title : str) -> None :
		self.__title = title

	@property
	def pubDate(self) -> str :
		return self.__pubDate

	@pubDate.setter
	def pubDate(self, pubDate : str) -> None :
		self.__pubDate = pubDate
