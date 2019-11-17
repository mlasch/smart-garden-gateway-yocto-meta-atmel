KBRANCH ?= "v4.19/standard/base"

require recipes-kernel/linux/linux-yocto.inc

SRCREV_machine ?= "a915fbeae8ed987402f69666d90bef15a01c5823"
SRCREV_meta ?= "ad6f8b357720ca8167a090713b7746230cf4b314"

KCONFIG_MODE = "--alldefconfig"
KBUILD_DEFCONFIG_at91sam9x5 ?= "at91_dt_defconfig"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-4.19;destsuffix=${KMETA} \
           file://0001-rtlwifi-Fix-potential-overflow-on-P2P-code.patch \
           file://dts \
          "
LINUX_VERSION ?= "4.19.78"

# Add meta-atmel kmeta
FILESEXTRAPATHS_prepend := "${THISDIR}:${THISDIR}/${BPN}-${LINUX_VERSION}:"
SRC_URI_append = " file://atmel-kmeta;type=kmeta;name=atmel-kmeta;destsuffix=atmel-kmeta"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

PV = "${LINUX_VERSION}+git${SRCPV}"
PR_append = ".1"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"

COMPATIBLE_MACHINE = "at91sam9x5"

# Functionality flags
KERNEL_FEATURES_append_gardena-sg-at91sam = " bsp/gardena-sg-at91sam/gardena-sg-at91sam.scc "
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc"
KERNEL_FEATURES_append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/scsi/scsi-debug.scc", "" ,d)}"

do_patch_append() {
    cp ${WORKDIR}/dts/* ${S}/arch/arm/boot/dts/

    if ! grep -q "YOCTO_DTBS" "${S}/arch/arm/boot/dts/Makefile"; then
        printf '\n# YOCTO_DTBS\ndtb-$(CONFIG_SOC_AT91SAM9) += gardena_smart_gateway_at91sam.dtb\n' >> \
            ${S}/arch/arm/boot/dts/Makefile
    fi
}
