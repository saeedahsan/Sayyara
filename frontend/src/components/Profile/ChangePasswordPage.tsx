import React, { useState } from "react";
import { validatePassword } from '../../utilities/ValidationUtil';

interface ChangePasswordProps {
    setChangingPassword: (arg0: boolean) => void;
    setIsOldPasswordIncorrect: (arg0: boolean) => void;
    saveUserInfo: (arg0: string, arg1: string) => void;
    isOldPasswordIncorrect: boolean; userInfo: {
        firstName: string;
        lastName: string;
        username: string;
        email: string;
        phoneNumber: string;
} }

const ChangePasswordPage = (props: ChangePasswordProps) => {
    const [showPasswordsMatchErrorMsg, setShowPasswordsMatchErrorMsg] = useState<boolean>(false)
    const [newPassword, setNewPassword] = useState<string>('')
    const [oldPassword, setOldPassword] = useState<string>('')
    const [confirmPassword, setConfirmPassword] = useState<string>('')

    const checkPassword = (oldPassword: string, newPassword: string, confirmPassword: string) => {
        var valid = true
        if (newPassword !== confirmPassword) {
            valid = false
            setShowPasswordsMatchErrorMsg(true)
        }
        if (validatePassword(newPassword) !== "") {
            valid = false
        }
        if (valid) {
            props.saveUserInfo(oldPassword, newPassword)
        }
    }

    const onOldPasswordChange = (value: string) => {
        setOldPassword(value)
        props.setIsOldPasswordIncorrect(false)
    }

    const onConfirmPasswordChange = (value: string) => {
        setConfirmPassword(value)
        setShowPasswordsMatchErrorMsg(false)
    }

    return (
        <div className="mx-8">
            <div className='grid grid-cols-2 grid-rows-6 gap-1 mt-6 mb-6'>
                <div className="col-span-2 flow-root flex flex-row mt-6">
                    <label className='font-semibold float-left'>
                        Old Password
                    </label>
                    {props.isOldPasswordIncorrect ? <label className="text-red-500 italic float-right text-xs">Incorrect Password</label> : null}
                </div>
                <input className="col-span-2 shadow-sm appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight
                focus:outline-blue-500 focus:shadow-outline" type="password" id='oldPassword' placeholder="************" onChange={(e) => onOldPasswordChange(e.target.value)}/>
                <div className='col-span-2 flow-root flex flex-row mt-6'>
                    <label className='font-semibold float-left'>
                        New Password
                    </label>
                    {newPassword.length > 0 ? <label className="text-red-500 italic float-right text-xs">{validatePassword(newPassword)}</label> : null}
                </div>
                <input className="col-span-2 shadow-sm appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight
                focus:outline-blue-500 focus:shadow-outline" type="password" id='newPassword' placeholder="************" onChange={(e) => setNewPassword(e.target.value)}/>
                <div className="col-span-2 flow-root flex flex-row mt-6">
                    <label className='font-semibold float-left'>
                        Confirm Password
                    </label>
                    {showPasswordsMatchErrorMsg ? <label className="text-red-500 italic float-right text-xs">Passwords don't match</label> : null}
                </div>
                <input className="col-span-2 shadow-sm appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight
                focus:outline-blue-500 focus:shadow-outline" type="password" id='confirmPassword' placeholder="************" onChange={(e) => onConfirmPasswordChange(e.target.value)}/>
            </div>
            <div className='flex justify-evenly my-8'>
                <button className="transition duration-100 ease-in-out w-32 bg-white hover:bg-gray-100 text-black
                font-semibold py-2 px-4 rounded border border-black" type="button" onClick={() => props.setChangingPassword(false)}>
                    Cancel
                </button>
                <button className="transition duration-100 ease-in-out w-32 bg-blue-500 hover:bg-blue-700 text-white
                font-semibold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="button" onClick={() => checkPassword(
                        oldPassword,
                        newPassword,
                        confirmPassword
                    )}>
                    Save
                </button>
            </div>
        </div>
    )
}

export default ChangePasswordPage;