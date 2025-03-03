#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pwd.h>
#include <shadow.h>
#include <string.h>
#include <sys/types.h>
#include <crypt.h>

int main(int ac, char **av, char **ev)
{
	char username[64] ;
	char password[64] ;
	char line[512] ;
	struct passwd *pwp;
	struct spwd *spwd ; 

	fgets(line,512,stdin) ;

	sscanf(line, "LOGIN:%s %s",username,password) ;

	pwp = getpwnam(username) ;

	if (pwp == (struct passwd *)NULL) {
		fprintf(stdout, "FAILED: [%s] does not exists.\n", username) ;
		exit(1) ;
	}
	
	spwd = getspnam(pwp->pw_name) ;

	if (spwd == (struct spwd *)NULL) {
		fprintf(stdout, "FAILED: unable to get (shadow) password for %s\n", username) ;
		exit(1) ;
	}
	else {
		char *gen = crypt(password,spwd->sp_pwdp) ;
		if (strcmp(spwd->sp_pwdp,gen) == 0) {
			fprintf(stdout, "OK:\n") ;
			exit(0);
		}
		else {
			fprintf(stdout, "FAILED: Password did not match.\n") ;
			exit(1) ;
		}
	}
	exit(0) ;
}
