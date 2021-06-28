Feature: Is it Friday yet?
  Everybody wants to know when it's Friday

Background: 
Given an Amazon Brasil Google search

Scenario: 80% Of Shown Products Should Be Exclusively The Searched Product
When Search For Iphone Using The Search Bar
And Count The Total List Of Found Products
And Count The Total Of Iphone Items Found
Then Make Sure At Least 80% Of Items Found are Iphone

Scenario: The Higher Price In The First Page Can't Be Greater Than U$2000
When Search For Iphone Using The Search Bar
And Count The Total Of Iphone Items Found
And Find The The More Expensive Iphone In Page
And Convert Its Value To USD Using https://exchangeratesapi.io/ API
Then Make Sure The Converted Value Is Not Greater Than US"2000"



Then I quit the WebDriver
	
	
