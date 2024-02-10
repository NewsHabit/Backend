def refineArticle(originArticle : str, imgSummary : str) -> str:
	for summary in imgSummary :
		originArticle = originArticle.replace(summary.get_text(), '')
	refined = ""
	blocks = originArticle.split('\n')
	for block in blocks :
		line = ""
		words = block.split()
		for word in words :
			line += word + " "
		if line != "" :
			refined += line.removesuffix(' ') + "\n\n"
	return refined.removesuffix('\n').removesuffix('\n')

def refineAuthorEmail(authorEmail : str) -> map :
	idx = authorEmail.find("기자")
	author = authorEmail[:idx]

	email = authorEmail[idx + 2:]
	if not email[0].isalpha() :
		email = email.removeprefix(email[0])
	if not email[len(email) - 1].isalpha() :
		email = email.removesuffix(email[len(email) - 1])
	return {"author" : author, "email" : email}