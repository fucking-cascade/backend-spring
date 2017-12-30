#mvn clean package
osascript -e 'tell application "terminal"
    activate
    tell application "System Events" to keystroke "t" using command down
    repeat while contents of selected tab of window 1 starts with linefeed
        delay 0.01
    end repeat
    do script "./start_eureka.sh" in window 1
end tell'
#open -a iTerm.app ./start_eureka.sh
#bash ./start_base.sh
#bash ./start_services.sh