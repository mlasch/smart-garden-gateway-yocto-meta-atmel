HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"
PE = "1"
PR = "r1"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "e5aee22e4be75e75a854ab64503fc80598bc2004"

SRC_URI = " \
    git://git.denx.de/u-boot.git \
    file://mtdparts.patch \
    file://bbt.patch \
    file://ubienv_names.patch \
"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "at91sam9x5"
