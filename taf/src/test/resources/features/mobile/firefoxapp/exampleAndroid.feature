@SISTEMA
@ANDROID
Feature: Open Firefox app and type secure url and verify that secure shield icon is displayed

  Scenario Outline: Open Firefox app and type secure url and verify that secure shield icon is displayed, in system "<so>"
    Given that the user opens the Firefox app on the operating system "<so>"
    And close welcome screen
    When type url "<url>"
    And type "<ENTER>" key
    And dont allow permission for notifications
    Then then a safe shield icon will be displayed

    Examples:
      | so      | url                     |
      | Android | https://www.google.com/ |
