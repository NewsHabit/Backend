class News :
	__article = ""
	__author = ""
	__description = ""
	__email = ""
	__imgLink = ""
	__source = ""
	__title = ""

	def __init__(self) :
		pass

	@property
	def article(self) -> str :
		return self.__article

	@article.setter
	def article(self, article : str) -> None :
		self.__article = article

	@property
	def author(self) -> str :
		return self.__author

	@author.setter
	def author(self, author : str) -> None :
		self.__author = author

	@property
	def description(self) -> str :
		return self.__description

	@description.setter
	def description(self, description : str) -> None :
		self.__description = description

	@property
	def email(self) -> str :
		return self.__email

	@email.setter
	def email(self, email : str) -> None :
		self.__email = email

	@property
	def imgLink(self) -> str :
		return self.__imgLink

	@imgLink.setter
	def imgLink(self, imgLink : str) -> None :
		self.__imgLink = imgLink

	@property
	def source(self) -> str :
		return self.__source

	@source.setter
	def source(self, source : str) -> None :
		self.__source = source

	@property
	def article(self) -> str :
		return self.__article

	@article.setter
	def article(self, article : str) -> None :
		self.__article = article

	@property
	def title(self) -> str :
		return self.__title

	@title.setter
	def title(self, title : str) -> None :
		self.__title = title
