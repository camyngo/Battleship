#!/bin/bash
#===============================================================================
# Team D'Buggers (Team 7)
# Scott Moser, Nicholas Benyo, Melissa Jones
# Professor McKee
# CPSC 5200-01
# 6 June 2019
#                              Milestone #2
#
# File: runTestSuite.sh
#
# Description:
# This shell script executes all J-Unit test cases in the $OUT_DIR directory
# a specificed number of times and e-mails the results to an e-mail address, if
# provided.
#
#   DEPENDENCIES, LIMITATIONS, & DESIGN NOTES:
#       Dependencies : 
#           1. JDK 1.8.0_211 must be installed at $JDK_PATH.
#       Design Notes :
#           1. All J-Unit test cases in the $OUT_DIR directory are run.
#           2. Console output is logged to $LOG_FILE.
#           3. Caller can specify the number of test iterations.
#           4. Caller can optionally specify an e-mail address.
#           5. If an e-mail is provided, the test results are set and the
#              console output is attached to the e-mail.
#           6. Test results and statistics are calculated and displayed.
#       Limitations :
#           1. Due to memory limitations on SU's CS1 server, the $CS1_HACK
#              variable is used to limit the memory used by the JVM.
#
#   Example Usage:
#   1. Executes the test suite one time
#         ./runTestSuite.sh 1
#   2. Executes the test suite twice and e-mails results to test@gmail.com.
#         ./runTestSuite.sh 2 test@gmail.com
#   3. Executes the test suite twice and e-mails the results to multiple 
#      recipients.
#         ./runTestSuite.sh 2 "test1@gmail.com test2@gmail.com test3@gmail.com"
#===============================================================================

#set -o errexit
set -o pipefail
set -o nounset
#set -o xtrace

#===============================================================================
# Constants
#===============================================================================
MIN_ARGS=1
MAX_ARGS=2

OUT_DIR="out"
PROJECT_ROOT=".."
JDK_PATH="$PROJECT_ROOT/../jdk1.8.0_211/bin"
JUNIT_JAR="junit/junit-platform-console-standalone-1.5.0-M1.jar"

# Hack required due to memory limitations on CS1
CS1_HACK="-Xmx512m"  # Limit heap to 512 MB

LOG_FILE="runTestSuiteLog.txt"

#===============================================================================
# Script
#===============================================================================

# Check number of arguments
if [ "$#" -lt "$MIN_ARGS" ] || [ "$#" -gt "$MAX_ARGS" ]; then
    echo "ERROR: Invalid number of command-line arguments!"
    echo "Usage:"
    echo "   ./runTestSuite <numIter> [emailRecipient]"
    echo "     - <numIter> - Number of times to run the test suite. Range: [1,10000)"
    echo "     - [emailRecipient] - Optional e-mail address to notify with test results"
    exit 1
fi

# Basic input validation for number of iterations
if [ "$1" -le "0" ]; then
    echo "ERROR: Caller must enter numIter of 1 or more!"
    exit 2
fi

# Remove previous log files
echo Cleaning up stale log files...
rm -f -v $LOG_FILE

# Capture system-level information for future debug-ability
echo -e "
================================================================================
Environment Variables
================================================================================
$(env)" >> $LOG_FILE

echo -e "
================================================================================
Java Version
================================================================================
" | tee -a $LOG_FILE
echo $($JDK_PATH/java $CS1_HACK -version 2>&1) | tee -a $LOG_FILE
echo JUnit jar file: $JUNIT_JAR | tee -a $LOG_FILE

echo -e "
================================================================================
Test Suite Execution
================================================================================
" | tee -a $LOG_FILE

# Run the test suite
numIter=$1
numPass=0
numFail=0
echo "Start date/time:" $(date)  | tee -a $LOG_FILE
start=$SECONDS
for i in `seq 1 $numIter`; do
    echo "**************************************************" | tee -a $LOG_FILE
    echo "Executing test run $i of $1..." | tee -a $LOG_FILE
    echo "**************************************************" | tee -a $LOG_FILE
    $JDK_PATH/java $CS1_HACK -jar $PROJECT_ROOT/$JUNIT_JAR --class-path $PROJECT_ROOT/$OUT_DIR --scan-class-path | tee -a $LOG_FILE
    rv=$?
    echo "Tests completed with return code: $rv" | tee -a $LOG_FILE
    if [ $rv == 0 ]; then
        let "numPass++"
    else
        let "numFail++"
    fi
done
stop=$SECONDS
echo "Stop date/time:" $(date) | tee -a $LOG_FILE

# Calculate statistics
  # Total execution time
duration=$(( $stop - $start ))
  # Search log file for the number of test cases per test suite run
numCases=$(grep -i -m1 "tests found" $LOG_FILE | grep -o -E '[0-9]+')
  # Multiply test cases per run times the number of runs
numCases=$((numCases * numIter))
  # Search the log file for the number of failing test cases
numCaseFail=$(grep -i -c "AssertionFailedError" $LOG_FILE)
  # The remainder are passing tests
numCasePass=$((numCases - numCaseFail))
  # Calculate the individual test case passing rate
testCasePassRate=$(bc -l <<< "scale=2; $numCasePass/$numCases*100")
  # Calculate the test suite passing rate
testSuitePassRate=$(bc -l <<< "scale=2; $numPass/$numIter*100")

# Determine overall result
rval=3
status="FAIL"
if [ "$numPass" -eq "$numIter" ]; then
    rval=0
    status="PASS"
fi
  
# Report statistics
echo ""                                                                                 | tee -a $LOG_FILE
echo "================================================================================" | tee -a $LOG_FILE
echo "Results & Statistics"                                                             | tee -a $LOG_FILE
echo "================================================================================" | tee -a $LOG_FILE
echo "Overall test suite result:  $status"                                              | tee -a $LOG_FILE
echo "Execution time:             $duration [seconds]"                                  | tee -a $LOG_FILE
echo ""                                                                                 | tee -a $LOG_FILE
echo "# of test suite runs:       $numIter"                                             | tee -a $LOG_FILE
echo "# of passing runs:          $numPass"                                             | tee -a $LOG_FILE
echo "# of failing runs:          $numFail"                                             | tee -a $LOG_FILE
echo "Test suite passing rate:    $testSuitePassRate%"                                  | tee -a $LOG_FILE
echo ""                                                                                 | tee -a $LOG_FILE
echo "# of test cases run:        $numCases"                                            | tee -a $LOG_FILE
echo "# of passing test cases:    $numCasePass"                                         | tee -a $LOG_FILE
echo "# of failing test cases:    $numCaseFail"                                         | tee -a $LOG_FILE
echo "Test case passing rate:     $testCasePassRate%"                                   | tee -a $LOG_FILE
echo "================================================================================" | tee -a $LOG_FILE
echo "Please see the log file for the full console output: $LOG_FILE"
echo ""

# Notify e-mail recipient if one was provided by caller
if [ $# == $MAX_ARGS ]; then
    echo "Sending e-mail report to $2..."
    messageBody="Please view the attached file for full console output of the test run."
    echo $messageBody | mail -s "[$USER] Test Suite Run -- $status!" -a $LOG_FILE $2
    if [ $? != 0 ]; then
        echo "ERROR: Unable to send e-mail! rv = $?"
        exit 4
    fi
fi

# Return status code
exit $rval
