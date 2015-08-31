var del = require('del'),
    argv = require('yargs').argv,
    gulp = require('gulp'),
    concat = require('gulp-concat');

var DEV_DOMAIN = 'www.helloscala.test',
    TEST_DOMAIN = 'test.helloscala.test',
    PROD_DOMAIN = 'www.helloscala.com';

if (argv.prod || argv.test) {
    process.env.NODE_ENV = 'production';
}

///////////////////////////////////////////////////////////////////////
// Vendor
///////////////////////////////////////////////////////////////////////
gulp.task('build:vendor:all', function () {
    return gulp.src(['./src/vendor/**/*'], {base: './src/vendor'})
        .pipe(gulp.dest('./dist/assets/vendor'))
});
gulp.task('build:vendor', ['build:vendor:all']);
gulp.task('watch:vendor', function () {
    gulp.watch('./src/vendor/**/*', ['build:vendor;all']);
});


gulp.task('build', ['build:vendor']);
