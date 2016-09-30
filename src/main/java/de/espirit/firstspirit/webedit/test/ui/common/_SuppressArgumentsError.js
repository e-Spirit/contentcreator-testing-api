/**
 * NOTE:
 * This script is just there to suppress the 'arguments' usage error outside of a function.
 * Selenium expects the function body on executeScript call, so it's ok to use 'arguments'
 * in the test scripts in this package.
 * Unfortunally IntelliJ does not provide an inspection for this error, so we use an unused
 * script file to fake a declared variable. Beautiful, isn't it? ;-)
 *
 * @type {Array|*}
 */
//noinspection ConstantIfStatementJS
arguments = arguments || [];