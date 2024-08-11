# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-arkruk;protocol=ssh;branch=master"
SRC_URI += " file://update_make_path.patch "

PV = "1.0+git${SRCPV}"
SRCREV = "bd8a823f324737525c00cd92de7cc2cb6f28ac9f"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/server/aesdsocket.c"
FILES:${PN} += "${bindir}/server/Makefile"
FILES:${PN} += "${bindir}/server/aesdsocket-start-stop"

TARGET_LDFLAGS += "-pthread -lrt"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${bindir}
	install -m 0755 ${S}/aesdsocket ${D}${bindir}/
	install -d 644 ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/
	install -d ${D}${sysconfdir}/rc5.d
	ln -sf ../init.d/aesdsocket-start-stop ${D}${sysconfdir}/rc5.d/S99aesdsocket
    ln -sf ../init.d/aesdsocket-start-stop ${D}${sysconfdir}/rc5.d/S99aesdsocket
}

FILES_${PN} = "${sysconfdir}/init.d"
